package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    public MyDecompressorInputStream(InputStream other){
        in = other;
    }
    @Override
    public int read(byte[] b) throws IOException {
        int curr;
        int count =8;
        int place =0;
        //read the num of rows and columns
        while (count >0 && (curr = in.read()) != -1 ) {
            b[place] = (byte) curr;
            place++;
            count--;
        }
        //decompressing for the maze
        count = b.length-24;
        int tmp =0;
        while (count >0 &&(curr = in.read()) != -1) {
            for (int i=0; i<curr; i++){
                b[place] = (byte)tmp;
                place++;
                count--;
            }
            if(tmp ==0) tmp =1;
            else tmp=0;
        }
        // read the positions of start and end of the maze
        count = b.length - place;
        while (count >0 &&(curr = in.read()) != -1) {
            b[place] = (byte) curr;
            place++;
            count--;
        }
        return 0;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
