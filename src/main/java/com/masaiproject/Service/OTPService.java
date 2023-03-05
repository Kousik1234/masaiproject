package com.masaiproject.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.masaiproject.Entity.OtpEntity;
import com.masaiproject.Repositry.OtpRepo;



@Service
public class OTPService {

	 @Autowired
	  private OtpRepo otpRepo;
	
		private static final Integer EXPIRE_MINS = 1;
		
		
	    private LoadingCache<String, Integer> otpCache;
	    
	    
	    public OTPService(){
		      super();
		      otpCache = CacheBuilder.newBuilder().
		      expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
		      .build(new CacheLoader<String, Integer>() {
		      public Integer load(String key) {
		             return 0;
		       }
		   });
	 }
	
	    
	    public Optional<OtpEntity> getdetails(int otpnum) {
			
			
			return otpRepo.findById(otpnum);
			
			
			
		}
	    
	    public int generateOTP() {
	    	
	    	Random random = new Random();
	    	
	    	int otp = 100000 + random.nextInt(900000);
	    	
	    	return otp;
	    	
	    }
	    
	    
	    
	    
	    
		 
		 public int getOtp(String key){ 
			 
			try{
			 return otpCache.get(key); 
			}catch (Exception e){
			 return 0; 
			}
			
		 }
		
		public void clearOTP(String key){ 
			
		     otpCache.invalidate(key);
		     
		 }
			
	
}