package creative.air.datastructure.map;

import creative.air.datastructure.map.AirHashMap.AirEntry;
/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 13/08/2009
 * @since  0.0.1
 * @version 0.0.1
 */
public class HashAnalysis   {
	public static void main(String[] args) {
		float loadFactor=0.75f;
		int loop=100;
		AirHashMap map = new AirHashMap(16, loadFactor);

		for (int i = 0; i < loop; i++) {
			map.put(i + ""+i, i); //indexFor(hash, table.length);
		}		
		
		int one=0;
		int two=0;
		int three=0;
		int gtthree=0;
		StringBuilder buffer=new StringBuilder();
		
		for (int i = 0; i < map.getTable().length; i++) {
			AirEntry e = map.getTable()[i];
			
			if (e != null && e.next != null) {
				int count=1;
				buffer.append("table[" + i + "]=\n");
				while (e.next != null){
					buffer.append("  e.hash="+e.hash);
					buffer.append("  e.value="+e.value+"\n");
					e = e.next;
					count++;
				}
				
				buffer.append("  e.hash="+e.hash);
				buffer.append("  e.value="+e.value+"\n");
				
				switch(count){
				case 2:{
					two++;
					break;
				}
				case 3:{
					three++;
					break;
				}
				default:
				gtthree++;
				}
			}else if(e!=null){
				one++;
			}
		}
		
		System.out.println(" ======================================================");
		System.out.println(" loadFactor="+loadFactor);
		System.out.println(" loop="+loop);
		
		System.out.println(" map table len="+map.getTable().length);
		System.out.println(" map len="+map.size());
		System.out.println(" one entry count= "+one);
		System.out.println(" two entries count= "+two);
		System.out.println(" three entries count= "+three);
		System.out.println(" greate than three entries count= "+gtthree);
		System.out.println(" ======================================================");
		System.out.println(buffer.toString());
	}

}
