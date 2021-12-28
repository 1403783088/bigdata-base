package base.spark.dao.oracle.impl

import base.spark.dao.oracle.BaseOracleDao
import org.apache.spark.sql.SparkSession

/**
 * Author: 260371
 * Date: 2021/11/8
 * Time: 14:18
 * Created by: 聂嘉良
 */
class OracleDaoImpl(_spark: SparkSession) extends BaseOracleDao {
  override var spark: SparkSession = _spark
}
