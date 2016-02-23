import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.io.IOException;
import java.net.*;
import java.util.*;
public class server extends Thread
{
	final int port=9999;
	private Vector<SocketChannel> user=new Vector<>(); 
	private InetSocketAddress addr=null;
	private Selector selector=null;
	private ServerSocketChannel ssc=null;
	private ServerSocket serverSocket=null;
	
	/**
	 * ~ )@_@)~
	 * ���� �ּ� ����
	 */
	public server()
	{
		try 
		{
			selector = Selector.open();
			ssc=ServerSocketChannel.open();
			ssc.configureBlocking(false);
			addr=new InetSocketAddress(port);
			serverSocket=ssc.socket();
			serverSocket.bind(addr);
			ssc.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {e.printStackTrace();}

	}
	
	public void start() 
	{
        try 
        {
        	while (true) 
            {
               selector.select();
               Iterator it = selector.selectedKeys().iterator();
             
             
            	   while(it.hasNext())
                   {
            		   SelectionKey key = (SelectionKey) it.next();                  
            		   
            		   if (key.isAcceptable())
            		   {
            			   accept(key);                  
            		   }
            		   else if (key.isReadable())                 
            		   {                	   
            			   read(key);
               			   it.remove();
            		   }
                   }
                // end of inner while 
            }
        } catch (Exception ex) { ex.printStackTrace();  }
    }//start end

   private void accept(SelectionKey key) 
   {
	   
       ServerSocketChannel server = (ServerSocketChannel) key.channel();
       try 
       {
            SocketChannel sc = server.accept();
            // ������ ����ä���� �� ���ŷ�� �б� ���� �����Ϳ� ����Ѵ�.

           if (sc == null)
                return;

           sc.configureBlocking(false);
           user.add(sc);
           sc.register(selector, SelectionKey.OP_READ);

        } catch (Exception ex) {ex.printStackTrace();}
    }
   //accept end

   private void read(SelectionKey key) throws IOException 
   {
       SocketChannel sc = (SocketChannel) key.channel(); // ByteBuffer�� �����Ѵ�.
       ByteBuffer buffer = ByteBuffer.allocateDirect(512);
       
       try 
       {
    	   if(sc.read(buffer)<0)
		   {
			   try 
		       {
		            sc.close();
		       } 
			   catch (IOException e) 
			   {
				   e.printStackTrace();
			   }
			   user.remove(sc);
		   }
		   
       } 
       catch (IOException e) 
       {
    	   e.printStackTrace();
    	   sc.close();
    	   user.remove(sc);
       }

       try 
       {
    	   broadcast(buffer);
       }
       catch (IOException ex) 
       {
            ex.printStackTrace();
       }	
       if (buffer != null) 
       {
            buffer.clear();
            buffer = null;
       }
    }

   private void broadcast(ByteBuffer buffer) throws IOException 
   {
        buffer.flip();
        Iterator iter = user.iterator();
       
        while (iter.hasNext()) 
        {
           SocketChannel sc = (SocketChannel) iter.next();
           if (sc != null)
           {
                sc.write(buffer);	
                buffer.rewind();
           }
        }
    }
	
	public static void main(String args[])
	{	
		new server().start();
	}
}