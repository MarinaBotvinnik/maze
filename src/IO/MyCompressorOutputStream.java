package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out){
        this.out = out;
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (b.length > 0) {
            //write the num of rows and columns (first 8 bytes)
            for (int i = 0; i < 8; i++) {
                out.write(b[i]);
            }
            //write the maze in a compressed way
            int curr = 0;
            int count = 0;
            for (int i = 8; i < b.length-16; i++) {
                if (b[i] == curr)
                    count++;
                else {
                    while(count > 255){
                        out.write(255);
                        out.write(0);
                        count = count -255;
                    }
                    if(count >0)
                        out.write(count);
                    if(i==8 && count == 0) // if the first number is 1.
                        out.write(count);
                    curr=b[i];
                    count =1;
                }
            }
            while(count > 255){
                out.write(255);
                out.write(0);
                count = count -255;
            }
            if(count >0)
                out.write(count);
            //write the positions of the start and the end of the maze
            for (int i = b.length-16; i < b.length; i++) {
                out.write(b[i]);
            }
        }
        System.out.println();
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }
}
