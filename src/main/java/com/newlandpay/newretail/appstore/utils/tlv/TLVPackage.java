package com.newlandpay.newretail.appstore.utils.tlv;

import java.util.Enumeration;

/**
 * 
 * @author lance
 * @since ver1.0
 */
public interface TLVPackage {
	
	public Enumeration elements();
	
	public void append(TLVMsg tlvToAppend) ;
	
	public void append(int tag, byte[] value) ;
	
	public void append(int tag, String value);
	
	public void deleteByIndex(int index) ;
	
	public void deleteByTag(int tag);
	
	public TLVMsg find(int tag);
	
	public int findIndex(int tag);
	
	public TLVMsg findNextTLV() ;
	
	public String getString(int tag) ;
	
	public byte[] getValue(int tag);
	 
	public boolean hasTag(int tag);
	
	public void unpack(byte[] buf, int offset, int len);
	
	public void unpack(byte[] buf);
	
	public byte[] pack();

}
