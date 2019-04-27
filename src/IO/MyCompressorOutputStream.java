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
            //write the num of rows and columns (first 4 bytes)
            for (int i = 0; i < 4; i++) {
                System.out.print(b[i]+ " ,");
                out.write(b[i]);
            }
            //write the maze in a compressed way
            int curr = 0;
            int count = 0;
            for (int i = 4; i < b.length-8; i++) {
                if (b[i] == curr)
                    count++;
                else {
                    while(count > 255){
                        out.write(255);
                        out.write(0);
                        count = count -255;
                    }
                    System.out.print(count+ ", ");
                    out.write(count);
                    curr=b[i];
                    count =1;
                }
            }
            //write the positions of the start and the end of the maze
            for (int i = b.length-8; i < b.length; i++) {
                System.out.print(b[i]+ " ,");
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
