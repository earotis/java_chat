import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.*;


public class clients extends Thread
{
	final int port=9999;
    static Selector selector = null;
    private SocketChannel sc = null;
  
    public void start()
    {
    	Receiver reciver=new Receiver();
    	Sender sender=new Sender(); 
    	reciver.start();
    	sender.start();	
    }
    
    public clients()
    {
    	try
        {
            selector = Selector.open();
            sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        } 
    	catch (Exception ex) {ex.printStackTrace();}
    }    
    
    class Receiver extends Thread
    {
    	public void run()
    	{
    		try
    		{
    			while(true)
    			{
    				clients.selector.select(); 
    				Iterator iter=clients.selector.selectedKeys().iterator();
    				while(iter.hasNext())
    				{
    				     SelectionKey key = (SelectionKey) iter.next();
    	                 if(key.isReadable()) 
    	                 {
    	                    read(key);
    	                 }
    	                 iter.remove();              
    				}
    			}
    		}
    		catch(Exception e)
    		{e.printStackTrace();}
    	}
        private void read(SelectionKey key) throws IOException 
        {
             SocketChannel sc = (SocketChannel) key.channel();
             ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
             sc.read(buffer);
             
             byte[] bytes = new byte[buffer.position()];
             buffer.flip();
             buffer.get(bytes);
             String s = new String(bytes);
             System.out.println(s);
             
			 buffer.clear();
         }//end read
    }
    class Sender extends Thread
    {
    	public void run() 
    	{
    		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    		try 
    		{
    			while (true) 
    			{
    				Scanner scanner = new Scanner(System.in);
    				String message = scanner.nextLine();
    				buffer.clear();
    				buffer.put(message.getBytes());
    				buffer.flip();
    				sc.write(buffer);
    			}
    		} catch (Exception ex) {} 
    		finally 
    		{
    			buffer.clear();
    		}
    	}//end startwriter
    }

    public static void main(String []args)
    {
    	clients c=new clients();
    	c.start();
    }
}
