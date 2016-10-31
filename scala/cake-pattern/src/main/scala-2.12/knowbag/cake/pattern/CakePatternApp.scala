package knowbag.cake.pattern

/**
  * Created by dev-williame on 10/31/16.
  */
object CakePatternApp extends App {

  trait OnOffDeviceComponent {
    val onOff: OnOffDevice

    trait OnOffDevice {
      def on: Unit
      def off: Unit
    }
  }

  trait SensorDeviceComponent {
    val sensor: SensorDevice

    trait SensorDevice {
      def isCoffeePresent: Boolean
    }
  }

  trait OnOffDeviceComponentImpl extends OnOffDeviceComponent {
    class Heater extends OnOffDevice {
      def on = println("heater.on")
      def off = println("heater.off")
    }
  }

  trait SensorDeviceComponentImpl extends SensorDeviceComponent {
    class PotSensor extends SensorDevice {
      def isCoffeePresent: Boolean = true
    }
  }

  trait WarmerComponentImpl {
    this: SensorDeviceComponent with OnOffDeviceComponent =>
    class Warmer {
      def trigger = {
        if (sensor.isCoffeePresent) onOff.on
        else onOff.off
      }
    }
  }

  object ComponentRegistry extends OnOffDeviceComponentImpl with SensorDeviceComponentImpl with WarmerComponentImpl {
    val onOff = new Heater
    val sensor = new PotSensor
    val warmer = new Warmer
  }

  val warmer = ComponentRegistry.warmer
  warmer.trigger
}
