package base.spark.service


import base.spark.common.Constants
import base.spark.util.{ActorUtil, DataFrameUtil, HttpUtil, KerberosUtil, TableImportUtil}
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession

/**
 * Author: 260371
 * Date: 2021/11/9
 * Time: 19:09
 * Created by: 聂嘉良
 */
class MyLogic(spark: SparkSession) {

  def logic(): Unit = {
    new KerberosUtil().krbLoginedUGI(Constants.KRB_USER, Constants.KEYTAB_URL, Constants.KRB5_URL)

//    new TableImportUtil(spark).accessTables()

    println(Constants.IMPALA_URL)
  }
}