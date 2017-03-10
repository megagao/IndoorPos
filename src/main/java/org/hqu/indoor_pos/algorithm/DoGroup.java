package org.hqu.indoor_pos.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hqu.indoor_pos.bean.BleBase;
import org.hqu.indoor_pos.bean.Group;

/**
 * created on 2016年8月22日
 *
 * 根据基站id进行分组，并分别求每个基站rssi的去除极端值的均值
 * 
 * @author  megagao
 * @version  0.0.1
 */
public class DoGroup {
	
	/*用来求组合数的数组*/
	private Integer[] a; 
	
	/**
	 * 根据传进来的基站列表，将基站进行分组，得到每个基站rssi的去除极端值的均值所组成的列表
	 *
	 * @param  str  接收到的一组基站组成的字符串(格式为“id,rssi;id,rssi........id,rssi;terminalID”)
	 * @return  返回每个基站rssi的去除极端值的均值所组成的列表
	 */
	public ArrayList<BleBase> doGroup(String str) {
		
		Map<String, Group> groupedBases = group(str);
		
		/*如果接收到的不同基站信号小于3个，不能定位，直接返回*/
		if(groupedBases.size()<3){
			return null;
		}
		
		List<BleBase> uniqueBases = dealByGroup(groupedBases);
		
		/*如果接收到的基站信号个数大于3个，那么就取RSSI值最大的3个用来定位*/
		int len = uniqueBases.size();
		if(len>3){
			Collections.sort(uniqueBases);
			return new ArrayList<BleBase>(uniqueBases.subList(len-3, len));
		}
		return (ArrayList<BleBase>) uniqueBases;
	}
	
	/**
	 * 
	 * 根据传进来的基站列表，将基站进行分组
	 *
	 * @param  bases  接收到的一组基站对象列表
	 * @return  返回分好组的Map，其中，key为基站id，value为Group对象。
	 * 			Group对象封装了所有接收到的该基站的所有rssi值列表。
	 */
	public Map<String, Group> group(String str) {
		
		Map<String, Group> groupedBases = new HashMap<String, Group>();
		
		String[] str1 = str.split(";");
		
		/*由Set集合存放unique id值*/
		Set<String> ids = new HashSet<String>();
		
		for(int i=0;i<str1.length-1;i++){
			ids.add(str1[i].split(",")[0]);
		}
		
		/*for(BleBase base   bases){
			ids.add(base.getId());
		}
		*/
		for(String id : ids){
			groupedBases.put(id, new Group());
		}
		
		for(int i=0;i<str1.length-1;i++) {
			Group group = groupedBases.get(str1[i].split(",")[0]);
			group.getRssis().add(Integer.parseInt(str1[i].split(",")[1]));
		}
		
		return  groupedBases;
	}
	
	/**
	 * 
	 * 把根据id分组后的Map进行处理，得到每个id组中去掉首尾极端值的rssi均值，若某组的rssi值个数小于4，
	 * 				  则得到其中值，最后返回List对象，其中List存放的元素为最终参与计算的rssi值构造的基站对象
	 *
	 * @param  groups  根据id分组后的Map
	 * @return  返回List对象
	 */
	public ArrayList<BleBase> dealByGroup(Map<String, Group> groups){
		
		Integer r;
		
		List<BleBase> bases = new ArrayList<BleBase>();
		
		/*一共收到了几个基站的值*/
		int baseNum = groups.size();
		
		/*new一个对应大小的数组，用来求组合数*/
		a = new Integer[baseNum];
		
		int k = 0;
		
		@SuppressWarnings("rawtypes")
		Iterator it = groups.keySet().iterator();
		
		while(it.hasNext()) { 
			
			String id = (String) it.next(); 
			
	        Group g = groups.get(id);
	        
	        ArrayList<Integer> rssis = (ArrayList<Integer>) g.getRssis();
	        
	        int len = rssis.size();
	        
	        int len2 = len/4;
	       
	        /*如果收到的数值个数大于4，则取中间的一部分然后求均值*/
	        if(len>=4){
	        	int count = 0;
	        	for(int i=len2;i<len-len2;i++){
					count+=rssis.get(i);
				}
	        	r = count/(len-2*len2);
	        }else if(len==1){
	        	/*如果收到的数值个数等于1，就用该数*/
		        r = rssis.get(0);
	        }else{
	        	/*如果收到的数值个数小于4，则求中位数*/
		        r = getMedian(rssis);
	        }
	        
	        BleBase base = new BleBase(id, r);
	        bases.add(base);
	        
	        /*a[k]代表bases的第k个元素*/
	        a[k] = k;
			k++;
			
	        /*
			用每组的最大值
	        Collections.sort(rssis);
	        r=rssis.get(len-1);
	        BleBase base = new BleBase(id, r);
	        bases.add(base);
	        a[k] = k;
			k++;
			*/
		} 
		
		return (ArrayList<BleBase>) bases;	
	}
	
	/**
	 * 得到列表的中位数
	 *
	 * @param  ls  一个整型数列表
	 * @return  返回该List的中值
	 */
	public Integer getMedian(List<Integer> ls){
		
		Integer m;
		
		/*对列表进行排序*/
        Collections.sort(ls);
        
		if(ls.size()%2==0){
        	m = (ls.get((ls.size()/2)-1)+ls.get(ls.size()/2))/2;
        }else{
        	m=(ls.get(ls.size()/2));
        }
		
		return m;
	}

	public Integer[] getA() {
		return a;
	}

	public void setA(Integer[] a) {
		this.a = a;
	}
}
