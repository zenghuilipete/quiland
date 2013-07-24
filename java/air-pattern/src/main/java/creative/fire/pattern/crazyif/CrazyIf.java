package creative.fire.pattern.crazyif;

public class CrazyIf {
	public String communicate(Device device) {
		if (device.getDeviceType().equals("Tablet1")) {
			device.setProtocol("Android-Linux");
			return device.call("Hello Tablet1");
		} else if (device.getDeviceType().equals("Tablet2")) {
			device.setProtocol("Android-C-Linux");
			return device.call("Hello Tablet2");
		} else if (device.getDeviceType().equals("SetTopBox1")) {
			device.setProtocol("European");
			return device.call("Hello SetTopBox1");
		} else if (device.getDeviceType().equals("SetTopBox2")) {
			device.setProtocol("Chinese");
			return device.call("ni hao SetTopBox2");
			// else if ......
		} else {
			device.setProtocol("openwrt");
			return device.call("Hello Gateway105");
		}
	}

	public static void main(String[] args) {
		CrazyIf thiz = new CrazyIf();
		Device tablet = new Device("10.11.58.141", "Tablet2");
		String communication = thiz.communicate(tablet);
		System.out.println(communication);
	}
}
