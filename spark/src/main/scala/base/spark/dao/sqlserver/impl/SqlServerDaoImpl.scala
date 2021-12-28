package base.spark.dao.sqlserver.impl

import base.spark.dao.sqlserver.BaseSqlServerDao
import org.apache.spark.sql.SparkSession

/**
 * Author: 260371
 * Date: 2021/11/8
 * Time: 11:41
 * Created by: 聂嘉良
 */
class SqlServerDaoImpl(_spark: SparkSession) extends BaseSqlServerDao {
  override var spark: SparkSession = _spark
}
