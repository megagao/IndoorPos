package org.hqu.indoor_pos.algorithm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hqu.indoor_pos.bean.BleBase;
import org.hqu.indoor_pos.bean.Location;
import org.hqu.indoor_pos.server.Server;

import Jama.Matrix;

/**
 * created on 2016年8月22日
 *
 * 三边定位算法
 *
 * @author  megagao
 * @version  0.0.1
 */
public class Trilateral implements Dealer{
	
	/*定位结果*/
	private Location location;
	
	@Override
	public Location getLocation(String str){
		
		/*实例化定位结果*/
		location = new Location();
		
		/*分组*/
		DoGroup doGrouper = new DoGroup();
		ArrayList<BleBase> uniqueBases = doGrouper.doGroup(str);
		
		/*如果收到的基站个数小于3，不能定位，直接返回*/
		if(uniqueBases==null){
			return null;
		}
		
		String[] str1 = str.split(";");
		
		String terminalId = str1[str1.length-1];
		
		return calculate(uniqueBases,terminalId);
	}
	
	/**
	 * 计算定位坐标
	 * 
	 * @param  bases 接收到的一组基站对象列表(此处列表中的基站应当是id各异的)
	 * @return  返回定位坐标
	 */
	public Location calculate(List<BleBase> bases, String terminalId){
		
		int baseNum = bases.size();
		
		/*距离数组*/
		double[] distanceArray = new double[baseNum];
		
		String[] ids = new String[baseNum];
		
		int j = 0;
		
		/*得到环境影响因素的值*/
		Double[] envFactors = Server.envFactors.get(location.getRoomId());
		/*如果没有指定的环境因子,就用默认的*/
		if(envFactors == null){
			envFactors = Server.envFactors.get(0);
		}
		double height = envFactors[0];
		double n =  envFactors[1];
		double p0 =  envFactors[2];
		
		/*获得基站id*/
		for (BleBase base : bases) {
			ids[j] = base.getId();
			distanceArray[j] = base.getDistance(height, n, p0);
			j++;
		}
		
		int disArrayLength = distanceArray.length;
		
		double[][] a = new double[baseNum-1][2];
		
		double[][] b = new double[baseNum-1][1];
		
		/*数组a初始化*/
		for(int i = 0; i < 2; i ++ ) {
 			a[i][0] = 2*(Server.baseStationLocs.get(ids[i])[0]-Server.baseStationLocs.get(ids[baseNum-1])[0]);
			a[i][1] = 2*(Server.baseStationLocs.get(ids[i])[1]-Server.baseStationLocs.get(ids[baseNum-1])[1]);
		}
		
		/*数组b初始化*/
		for(int i = 0; i < 2; i ++ ) {
			b[i][0] = Math.pow(Server.baseStationLocs.get(ids[i])[0], 2) 
					- Math.pow(Server.baseStationLocs.get(ids[baseNum-1])[0], 2)
					+ Math.pow(Server.baseStationLocs.get(ids[i])[1], 2)
					- Math.pow(Server.baseStationLocs.get(ids[baseNum-1])[1], 2)
					+ Math.pow(distanceArray[disArrayLength-1], 2)
					- Math.pow(distanceArray[i],2);
		}
		
		/*将数组封装成矩阵*/
		Matrix b1 = new Matrix(b);
		Matrix a1 = new Matrix(a);
		
		/*求矩阵的转置*/
		Matrix a2  = a1.transpose();
		
		/*求矩阵a1与矩阵a1转置矩阵a2的乘积*/
		Matrix tmpMatrix1 = a2.times(a1);
		Matrix reTmpMatrix1 = tmpMatrix1.inverse();
		Matrix tmpMatrix2 = reTmpMatrix1.times(a2);
		
		/*中间结果乘以最后的b1矩阵*/
		Matrix resultMatrix = tmpMatrix2.times(b1);
		double[][] resultArray = resultMatrix.getArray();
		
		location.setxAxis(resultArray[0][0]);
		location.setyAxis(resultArray[1][0]);
		
		/*根据rssi值最大的基站查找相应的坐标系id*/
		location.setRoomId(Server.roomIds.get(ids[baseNum-1])); 
		
		/*查找该终端对应的员工id*/
		location.setEmpId(Server.empIds.get(terminalId));
		
		/*设置定位结果的时间戳*/
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		location.setTimeStamp(ts);
		
		return location;
	}
}
