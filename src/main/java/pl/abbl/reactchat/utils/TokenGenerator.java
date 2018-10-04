package pl.abbl.reactchat.utils;

public class TokenGenerator {
	private static final String AVAILABLE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz@-!#";
	
	public static String generateToken(int length) {
		StringBuilder builder = new StringBuilder();
		
		while(length-- != 0) {
			int characterIndex = (int)(Math.random() * AVAILABLE_CHARACTERS.length());
			builder.append(AVAILABLE_CHARACTERS.charAt(characterIndex));
		}
		System.out.println("Generated user token:" + builder.toString());
		return builder.toString();
	}
}
