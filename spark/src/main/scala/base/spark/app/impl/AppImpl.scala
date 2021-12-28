package base.spark.app.impl

import base.spark.app.BaseSparkApp
import base.spark.service.MyLogic

/**
 * Author: 260371
 * Date: 2021/11/8
 * Time: 17:04
 * Created by: 聂嘉良
 */
class AppImpl extends BaseSparkApp {
  /**
   * 在此处调用service层代码
   */
  override def onRun(): Unit = {
    val runSpark = spark
    new MyLogic(runSpark).logic()
  }
}

object AppImpl{
  def main(args: Array[String]): Unit = {
    new AppImpl().startApp()
  }
}
