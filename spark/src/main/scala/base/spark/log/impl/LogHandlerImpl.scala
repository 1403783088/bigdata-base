package base.spark.log.impl

import base.spark.log.BaseLogHandler
import org.apache.spark.sql.SparkSession

/**
 * Author: 260371
 * Date: 2021/11/24
 * Time: 15:10
 * Created by: 聂嘉良
 */
class LogHandlerImpl(_spark: SparkSession) extends BaseLogHandler{
  override var spark: SparkSession = _spark
}
