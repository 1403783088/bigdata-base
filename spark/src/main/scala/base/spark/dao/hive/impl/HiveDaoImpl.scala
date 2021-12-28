package base.spark.dao.hive.impl

import base.spark.dao.hive.BaseHiveDao
import org.apache.spark.sql.SparkSession

/**
 * Author: 260371
 * Date: 2021/8/5
 * Time: 14:39
 * Created by: 聂嘉良
 */
class HiveDaoImpl(_spark: SparkSession) extends BaseHiveDao {
  override var spark: SparkSession = _spark
}
