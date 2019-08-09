package com.newlandpay.newretail.appstore.utils.tlv;

/**
 * 
 * @author lance
 * @since ver1.0
 */
public interface TLVMsg {
	
    public int getTag() ;

    public byte[] getValue() ;

    public byte[] pack();
    
    public byte[] getL();
    
    public String toHexString();
    
}
