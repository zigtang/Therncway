package net.therncway.Util;

import java.util.Random;

import net.therncway.R;

public class ImageUtil {
	private static final int[] imgId = new int[] { R.drawable.emoji_1,
			R.drawable.emoji_2, R.drawable.emoji_3, R.drawable.emoji_4,
			R.drawable.emoji_5, R.drawable.emoji_6, R.drawable.emoji_7,
			R.drawable.emoji_8 };
	
	public static int getImagId(){
		Random random = new Random();
		int index = Math.abs(random.nextInt()) % imgId.length;
		return imgId[index];
	}
}
