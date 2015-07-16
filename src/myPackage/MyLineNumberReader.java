package myPackage;

import java.io.*;

//山寨版的LineNumberReader类，读取文本内容时可获得当前的代码行数,实现了Java中的LineNumberReader的功能
class MyLineNumberReader
{
	//这里用的是装饰器模式，具体的内容可参见本人的博文《打造山寨产品和伪造产品的利器——装饰模式》	
	private Reader fr;
	private int lineNumber=0;	//记录当前的行数
	private String filePath;
	private BufferedReader bufferedReader;
	
	
	MyLineNumberReader( Reader fr ,String filee)
	{
		this.fr=fr;
		this.filePath = filee;
		
		String encoding="UTF-8";
        File file=new File(filee);
        if(file.isFile() && file.exists()){ //判断文件是否存在
            InputStreamReader read;
			try {
				read = new InputStreamReader(
				new FileInputStream(file),encoding);
				this.bufferedReader = new BufferedReader(read);
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//考虑到编码格式
            
        }
	}
	
	/*
	通过read()函数一个一个字符的读取，当遇到回车换行就返回读取的一行字符串
	*/
	public String readLine()
	{
		int num=0;
		StringBuffer sb=new StringBuffer();
		try {
			while( (num=fr.read())!=-1 )
			{
				if( num=='\r')
					continue;
				else if( num=='\n')
				{
					lineNumber++;	//读取一行，行号加1
					return sb.toString(); //返回读取的一行字符串
				}
				else
				{
					sb.append(num );//(char)
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		//防止文本末尾没加回车换行符，以丢失文本内容
		if( sb.length()>0 )
		{
			lineNumber++;
			return sb.toString();
		}
		return null;
	}
	
	public String read(){
			try {
	                String lineTxt = null;
	                while((lineTxt = this.bufferedReader.readLine()) != null){
	                    return lineTxt;
	                }
	    } catch (Exception e) {
	        System.out.println("读取文件内容出错");
	        e.printStackTrace();
	    }
		return null;
	}
	
	//关闭输入流对象
	public void close() 
	{
		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//设置当前的行数
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber=lineNumber;
	}
	
	//获取当前的行数
	public int getLineNumber()
	{
		return lineNumber;
	}
}
