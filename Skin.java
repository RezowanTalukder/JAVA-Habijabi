package skin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author USER
 */
public class Skin {
    
    
    static int[][][] s_arr =new int[257][257][257];
    static int[][][] ns_arr =new int[257][257][257];

    public static void main(String[] args) throws IOException {
        
        readImages();
        
        testImage("me.jpg", 0.25);

    }
    
    public static void readImages() throws IOException
    {
        String f1 =".bmp" ;
        String ff1=".jpg" ;
        

        
        for(int i=0 ;i<257 ;i++){
            for(int j=0 ;j<257 ;j++){
                for(int k=0 ;k<257 ;k++){
                    s_arr[i][j][k] = 0 ;
                    ns_arr[i][j][k] = 0 ;     
                }
            }
        }
        
        
        
        int limit = 5 ;
        int[] pixel = null;
        int[] m_pixel;
        int s=1 ,ns=0;
        
        for(int t=0 ;t<limit;t++){
                String f2 = String.format("%04d", t);
                
                String fileName= "C:\\ibtd\\"+f2+ff1 ;
                fileName = fileName.replace("\\", "\\\\");
                
                String m_fileName="C:\\ibtd\\Mask\\"+ f2+f1 ;
                m_fileName = m_fileName.replace("\\", "\\\\");
            
            
                BufferedImage bi=ImageIO.read(new File(fileName));
                BufferedImage m_bi=ImageIO.read(new File(m_fileName));
                
                
                
                //File _pix = new File("non_skin.txt") ;
                //FileWriter non_skin = new FileWriter(_pix,true) ;
                




                for (int y = 0; y < bi.getHeight(); y++) {
                    for (int x = 0; x < bi.getWidth(); x++) {
                        pixel = bi.getRaster().getPixel(x, y, new int[3]);
                        m_pixel = m_bi.getRaster().getPixel(x, y, new int[3]);
                        
                        if(m_pixel[0]>250 && m_pixel[1]>250 && m_pixel[2]>250){
                            //non_skin.write(pixel[0] + " " + pixel[1] + " " + pixel[2]+" "+ns+"\n") ;
                            ns_arr[pixel[0]][pixel[1]][pixel[2]]+=1 ;
                        }
                        else {
                            //skin.write(pixel[0] + " " + pixel[1] + " " + pixel[2]+" "+s+"\n") ;
                            s_arr[pixel[0]][pixel[1]][pixel[2]]+=1 ;
                        }
                        
                        System.out.println(pixel[0] + " " + pixel[1] + " " + pixel[2] + " "+s+"\n");
                        //System.out.println(t);
                                              
                }
            }
          
        }
        
        File pix = new File("skin.txt") ;
        FileWriter skin = new FileWriter(pix,true) ;
        
        for(int i=0 ;i<257 ;i++){
            for(int j=0 ;j<257 ;j++){
                for(int k=0 ;k<257 ;k++){
                    int nsX = ns_arr[i][j][k] ;
                    int sX = s_arr[i][j][k] ;
                    double ratio ;
                    
                    if(nsX==0)ratio=(double)sX ;
                    else ratio=(double)sX/nsX ;
                    
                    skin.write(i + " " + j + " " + k+" "+String.valueOf(ratio)+"\n") ;
                    System.out.println(ns_arr[i][j][k]) ; 
                    
                }
            }
        }
        
    }
    
    
    
   
    
    
    public static void testImage(String fileName, double threshold) throws IOException
    {
        BufferedImage img =ImageIO.read(new File(fileName));
        int[] pixel;
        
        
        double[][][] pix_array = new double[257][257][257];
        
        File file = new File("skin.txt") ;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();
            
            double y = scanner.nextDouble();
            
            pix_array[a][b][c] = y ;
                    
        }

        File f = null;
        
        for (int y = 0; y < img.getHeight(); y++) {
                    for (int x = 0; x < img.getWidth(); x++) {
                        pixel = img.getRaster().getPixel(x, y, new int[3]);
                        
                        int a,b,c,d ;
                        a=pixel[0] ;
                        b=pixel[1] ;
                        c=pixel[2] ;
                        d=pixel[3] ;
                        
                        double rat = pix_array[a][b][c] ;

                        
                        boolean skin = false ;
                        
                        if(rat>threshold){
                            int argb = (d << 24) +( a << 16 )+ (b << 8) +(c) ;

                            img.setRGB(x, y, argb);
                        }
                        /*lse if((double)sk/nos >threshold){
                        else if(sk>=nos){
                            skin = true ;

                            int argb = (d << 24) +( a << 16 )+ (b << 8) +(c) ;

                            img.setRGB(x, y, argb);

                        }
                          */ 
                        
                        else{
                            int argb = (255 << 24) + (255 << 16) + (255 << 8) + (255) ;

                            img.setRGB(x, y, argb); 
                        }
                     /*   
                        try{
                              img.setRGB(y, x, p);
                              f = new File("Output.jpg");
                              ImageIO.write(img, "jpg", f);
                              
                            }catch(IOException e){
                              System.out.println(e);
                        }
                     */   
            }
        }
        //for end
        //Graphics g = null;
        //g.drawImage(img, 0, 0,null);
        
        ImageIO.write(img, "jpg", new File("Output2.jpg"));
               
    }
  
}
