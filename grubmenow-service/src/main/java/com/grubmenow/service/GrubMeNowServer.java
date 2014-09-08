package com.grubmenow.service;


import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.grubmenow.service.*;
import org.apache.thrift.server.TServer.Args;

public class GrubMeNowServer 
{
	public static SearchFoodItemHandler handler;

	  public static GrubMeNowService.Processor processor;

	  public static void main(String [] args) {
	    try {
	      handler = new SearchFoodItemHandler();
	      processor = new GrubMeNowService.Processor(handler);

	      Runnable simple = new Runnable() {
	        public void run() {
	          simple(processor);
	        }
	      };      
	     
	      new Thread(simple).start();
	    } catch (Exception x) {
	      x.printStackTrace();
	    }
	  }

	  public static void simple(GrubMeNowService.Processor processor) {
	    try {
	      TServerTransport serverTransport = new TServerSocket(9090);
	      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

	      System.out.println("Starting the simple server...");
	      server.serve();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	 
}
