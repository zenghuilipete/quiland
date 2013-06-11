package creative.fire.poi.excelTranser;

public class TestXLParser {

	public static void main(String[] sss) {
		XLParser parser = new XLParser();
		print(parser, "directoryjsp__directory.sslport.manatory");
		print(parser, "devicejsp__location.delete.confirmation");
		print(parser, "statisticsjsp__history.summary.utilization.bw.c2");
		print(parser, "devicejsp__gk.sipOutboundProxyIp");
		print(parser, "resourcebasebiz__Terminal.ValueBean.Auto");
		print(parser, "utilbiz__eventmsg.Utilization_NetBandwidthInternal.cleared");
	}

	private static void print(XLParser parser, String path_key) {
		String path = parser.analysePath(path_key, Language.de_DE.name());
		System.out.println(path_key + "\t\t:\t" + path);
	}
}
