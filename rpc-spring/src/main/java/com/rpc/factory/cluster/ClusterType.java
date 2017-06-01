package com.rpc.factory.cluster;

/**
 * 集群类型枚举
 * @author Administrator
 */
public enum ClusterType {
	
	FAILOVER("failover",new FailOverCluster());
	
	private String name;
	private Cluster cluster;
	
	private ClusterType(String name,Cluster cluster){
		this.name = name;
		this.cluster = cluster;
	}
	
	public Cluster  getCluster(){
		return this.cluster;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static Cluster getCluster(String name){
		switch(name.toLowerCase()){
			case "failover":return FAILOVER.getCluster();
			default : return FAILOVER.getCluster();
		}
	}

}
