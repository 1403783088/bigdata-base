package base.spark.dao.sqlserver

import java.sql.DriverManager
import java.util.Properties

import base.spark.exception.impl.ExceptionHandlerImpl
import base.spark.util.DateUtil
import org.apache.log4j.Logger
import org.apache.spark.sql.{DataFrame, Dataset, Row, SaveMode, SparkSession}
import org.apache.spark.sql.functions._

import scala.util.{Failure, Success, Try}

/**
 * Author: 260371
 * Date: 2021/8/5
 * Time: 12:34
 * Created by: 聂嘉良
 */
abstract class BaseSqlServerDao {

  val logger: Logger = Logger.getLogger(getClass)

  var spark: SparkSession

  def read(url: String, userName: String, password: String, table: String): DataFrame = {
    //获取数据量
    val tableName = table.toLowerCase

    val connProp = new Properties()
    connProp.setProperty("user", userName)
    connProp.setProperty("password", password)
    connProp.setProperty("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")

    val reader = spark.read

    val row_count =
      Try[Int] {
        if (tableName.contains("view"))
          1000000
        else
          reader.format("jdbc")
            .option("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
            .option("url", url)
            .option("user", userName)
            .option("password", password)
            .option("dbtable", s"(SELECT A.NAME name,B.ROWS rows FROM sysobjects A JOIN sysindexes B ON A.id = B.id WHERE A.xtype = 'U' AND B.indid IN(0,1)) a")
            .load()
            .where(lower(col("name")).equalTo(tableName))
            .select("rows").first().getInt(0)
      }.getOrElse(200)


    Try[DataFrame] {

      val oneCol = Try[String]{
        reader.jdbc(url,
          "(SELECT " +
            "A.name AS table_name, " +
            "B.name AS column_name " +
            "FROM sys.tables A " +
            "INNER JOIN sys.columns B ON B.object_id = A.object_id) a", connProp)
          .where(lower(col("table_name")).equalTo(tableName))
          .first()
          .getAs[String]("column_name")
      }.getOrElse("")

      if(oneCol == ""){
        reader.jdbc(url, table, connProp)
      }else{
        reader.jdbc(url,
          s"(select *, row_number() over(order by $oneCol) as row_id from $table ) as $table",
          "row_id", 1, row_count, 200, connProp)
          .drop("row_id")
      }
    } match {
      case Success(df) =>
        df
      case Failure(e) =>
        e.printStackTrace()
        new ExceptionHandlerImpl(e).onException("获取SqlServer表"+table+"发生异常!")
        null
    }
  }

  def write(url: String, userName: String, pwd: String, table:String, df:DataFrame, mode:String): Unit = {
    val connectionProperties = new Properties()
    connectionProperties.setProperty("user", userName)
    connectionProperties.setProperty("password", pwd)
    connectionProperties.setProperty("characterEncoding", "utf8")
    val conn = DriverManager.getConnection(url, userName, pwd)
    if (mode == "overwrite") {
      val updateStmt = conn.createStatement()
      updateStmt.executeUpdate("truncate table " + table)
    }
    df.write.mode(SaveMode.Append).option("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver").jdbc(url, table, connectionProperties)
    logger.info("【" + table + "】->SqlServer at " + DateUtil.getTimeNow + "保存完成")
  }
}
