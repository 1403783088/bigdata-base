package base.spark.dao.mysql.impl

import base.spark.dao.mysql.BaseMySqlDao
import org.apache.spark.sql.SparkSession

/**
 * Author: 260371
 * Date: 2021/11/8
 * Time: 14:12
 * Created by: 聂嘉良
 */
class MySqlDaoImpl(_spark: SparkSession) extends BaseMySqlDao {
  override var spark: SparkSession = _spark
}
