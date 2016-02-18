package com.bluetooth;



public class DataBuffer {
	byte[] a; 
    int front;  
    int rear;   
    int cnt;   
    
    public DataBuffer(int size){  
        a = new byte[size];  
        front = 0;  
        rear =0;  
        cnt = 0;
    }
    
   
    public int enqueue(byte[] data,int len){  
    	
    	int i;
    	
    	for ( i=0;i<len;i++)
    	{
    		if((rear+1)%a.length==front)
    		{  
    			return i;  
    		}  
    		a[rear]=data[i];  
    		rear = (rear+1)%a.length;
    		cnt++;
    	}
        return i;  
    }  
    
   
    public int dequeue(byte[] data_out,int len){  
        if(rear==front){  
            return 0;  
        }  
        
        int i;
        for (i=0;i<len;i++)
        {
        	data_out[i] = a[front];  
        	front = (front+1)%a.length;
        	cnt--;
        	if (rear==front)
        	{
        		return i;
        	}
        }
       	return i;  
    }
    
  
    public int getSize(){
		return cnt;
    }

    
}

