package org.bit.linc.monitors;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class MonitorServiceImpl {

		public static MonitorBean getMonitorBean() throws SigarException{
			double memoryTotal=0;;
			double memoryUsed=0;;
			long fsTotal=0;
			long fsUsed=0;
			int cpuRatio=0;
			Sigar sigar = SigarFactory.getSigar();
			//get memory message
			{
		        Mem mem = sigar.getMem();
		        float Gb=1024*1024*1024;
		        // 内存总量
		        memoryTotal=mem.getTotal()/ Gb;
		        memoryUsed=mem.getUsed() / Gb;
			}
	        //-----------------
	        //get cpu message
			{
				CpuInfo infos[] = sigar.getCpuInfoList();
		        CpuPerc cpuList[] = null;
		        int cpuSum=0;
		        int usedSum=0;
		        cpuList = sigar.getCpuPercList();
		        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
		            CpuInfo info = infos[i];
		            cpuSum+=info.getMhz();
		            usedSum+=cpuList[i].getCombined()*cpuSum;
		        }
		        cpuRatio=usedSum*100/cpuSum;
			}
	        //---------------
	        //get fs message
			{
				FileSystem fslist[] = sigar.getFileSystemList();
				try{
					for (int i = 0; i < fslist.length; i++) {
		                FileSystem fs = fslist[i];
		                FileSystemUsage usage = null;
		                usage = sigar.getFileSystemUsage(fs.getDirName());
		                switch (fs.getType()) {
		                case 0: // TYPE_UNKNOWN ：未知
		                    break;
		                case 1: // TYPE_NONE
		                    break;
		                case 2: // TYPE_LOCAL_DISK : 本地硬盘
		                    // 文件系统总大小
		                	fsTotal+=usage.getTotal();
		                	fsUsed+=usage.getUsed();
		                    break;
		                case 3:// TYPE_NETWORK ：网络
		                    break;
		                case 4:// TYPE_RAM_DISK ：闪存
		                    break;
		                case 5:// TYPE_CDROM ：光驱
		                    break;
		                case 6:// TYPE_SWAP ：页面交换
		                    break;
		                }
		            }
				}catch(SigarException e){
					
				}
		        fsTotal=fsTotal/(1024*1024);
		        fsUsed=fsUsed/(1024*1024);
			}
	        //---------------
	        MonitorBean moBean=new MonitorBean(cpuRatio, memoryTotal, memoryUsed, (int)fsTotal, (int)fsUsed);
	        return moBean;
		}
}
