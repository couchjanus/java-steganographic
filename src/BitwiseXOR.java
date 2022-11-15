import java.awt.image.*;

public class BitwiseXOR {

	private int[] mapping(int index) {
		int mapping[] = new int[2];
		
		if(index <= 4) {
			mapping[0] = 7;
			mapping[1] = index-1; 
		}
		else if(index <= 8) {
			mapping[0] = 6;
			mapping[1] = index -4 -1; 
		}
		else if(index <= 12) {
			mapping[0] = 5;
			mapping[1] = index -8 -1; 
		}
		else if(index <= 16) {
			mapping[0] = 4;
			mapping[1] = index -12 -1; 
		}
		else if(index <= 19) {
			mapping[0] = 3;
			mapping[1] = index -16 -1; 
		}
		else if(index <= 22) {
			mapping[0] = 2;
			switch (index) {
			case 20:{
				mapping[1] = 0;
				break;
			}
			case 21:{
				mapping[1] = 1;
				break;
			}
			case 22:{
				mapping[1] = 3;
				break;
				}
			}
		}
		else if(index <= 25) {
			mapping[0] = 1;
			switch (index) {
			case 23:{
				mapping[1] = 0;
				break;
			}
			case 24:{
				mapping[1] = 2;
				break;
			}
			case 25:{
				mapping[1] = 3;
				break;
			}
		}
	}
		else if(index <= 28) {
			mapping[0] = 1;
			switch (index) {
			case 26:{
				mapping[1] = 1;
				break;
			}
			case 27:{
				mapping[1] = 2;
				break;
			}
			case 28:{
				mapping[1] = 3;
				break;
			}
		}
	}
	return mapping;
}
	public void xor(BufferedImage image, int bitwiseXORIndex, int pixelSize) {
		int mapping[] = mapping(bitwiseXORIndex);
		int sourceBit = mapping[0];
		int targetBit = mapping[1];
		
		byte img[] = getByteData(image);
		
		int bytePerPixel = pixelSize / 8;
		
		for (int i=0; i<img.length; i+=bytePerPixel) {
			for(int j=0; j < bytePerPixel; j++) {
				int sourceValue = (img[i+j]>>sourceBit)&1;
				int targetValue = (img[i+j]>>targetBit)&1;
				int singleBit = sourceValue ^ targetValue;
				if(singleBit == 0) {
					img[i+j]=0x00;
				}else {
					img[i+j]=(byte)128;
				}
			}
		}
		
		
	}
	
	public byte[] getByteData(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
		return buffer.getData();
	}
}