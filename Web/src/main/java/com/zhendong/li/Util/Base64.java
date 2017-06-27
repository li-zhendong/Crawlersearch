/**
 * 
 */
package com.zhendong.li.Util;

/*
 * Base64 encoding and decoding.
 * Copyright (C) 2001-2004 Stephen Ostermiller
 * http://ostermiller.org/contact.pl?regarding=Java+Utilities
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * See COPYING.TXT for details.
 */

import java.io.*;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Locale;

/**
 * Implements Base64 encoding and decoding as defined by RFC 2045: "Multipurpose
 * Internet Mail Extensions (MIME) Part One: Format of Internet Message Bodies"
 * page 23. More information about this class is available from <a target="_top"
 * href= "http://ostermiller.org/utils/Base64.html">ostermiller.org</a>.
 * 
 * <blockquote>
 * <p>
 * The Base64 Content-Transfer-Encoding is designed to represent arbitrary
 * sequences of octets in a form that need not be humanly readable. The encoding
 * and decoding algorithms are simple, but the encoded data are consistently
 * only about 33 percent larger than the unencoded data. This encoding is
 * virtually identical to the one used in Privacy Enhanced Mail (PEM)
 * applications, as defined in RFC 1421.
 * </p>
 * 
 * <p>
 * A 65-character subset of US-ASCII is used, enabling 6 bits to be represented
 * per printable character. (The extra 65th character, "=", is used to signify a
 * special processing function.)
 * </p>
 * 
 * <p>
 * NOTE: This subset has the important property that it is represented
 * identically in all versions of ISO 646, including US-ASCII, and all
 * characters in the subset are also represented identically in all versions of
 * EBCDIC. Other popular encodings, such as the encoding used by the uuencode
 * utility, Macintosh binhex 4.0 [RFC-1741], and the base85 encoding specified
 * as part of Level 2 PostScript, do no share these properties, and thus do not
 * fulfill the portability requirements a binary transport encoding for mail
 * must meet.
 * </p>
 * 
 * <p>
 * The encoding process represents 24-bit groups of input bits as output strings
 * of 4 encoded characters. Proceeding from left to right, a 24-bit input group
 * is formed by concatenating 3 8bit input groups. These 24 bits are then
 * treated as 4 concatenated 6-bit groups, each of which is translated into a
 * single digit in the base64 alphabet. When encoding a bit stream via the
 * base64 encoding, the bit stream must be presumed to be ordered with the
 * most-significant-bit first. That is, the first bit in the stream will be the
 * high-order bit in the first 8bit byte, and the eighth bit will be the
 * low-order bit in the first 8bit byte, and so on.
 * </p>
 * 
 * <p>
 * Each 6-bit group is used as an index into an array of 64 printable
 * characters. The character referenced by the index is placed in the output
 * string. These characters, identified in Table 1, below, are selected so as to
 * be universally representable, and the set excludes characters with particular
 * significance to SMTP (e.g., ".", CR, LF) and to the multipart boundary
 * delimiters defined in RFC 2046 (e.g., "-").
 * </p>
 * 
 * <pre>
 *                  Table 1: The Base64 Alphabet
 * 
 *   Value Encoding  Value Encoding  Value Encoding  Value Encoding
 *       0 A            17 R            34 i            51 z
 *       1 B            18 S            35 j            52 0
 *       2 C            19 T            36 k            53 1
 *       3 D            20 U            37 l            54 2
 *       4 E            21 V            38 m            55 3
 *       5 F            22 W            39 n            56 4
 *       6 G            23 X            40 o            57 5
 *       7 H            24 Y            41 p            58 6
 *       8 I            25 Z            42 q            59 7
 *       9 J            26 a            43 r            60 8
 *      10 K            27 b            44 s            61 9
 *      11 L            28 c            45 t            62 +
 *      12 M            29 d            46 u            63 /
 *      13 N            30 e            47 v
 *      14 O            31 f            48 w         (pad) =
 *      15 P            32 g            49 x
 *      16 Q            33 h            50 y
 * </pre>
 * <p>
 * The encoded output stream must be represented in lines of no more than 76
 * characters each. All line breaks or other characters no found in Table 1 must
 * be ignored by decoding software. In base64 data, characters other than those
 * in Table 1, line breaks, and other white space probably indicate a
 * transmission error, about which a warning message or even a message rejection
 * might be appropriate under some circumstances.
 * </p>
 * 
 * <p>
 * Special processing is performed if fewer than 24 bits are available at the
 * end of the data being encoded. A full encoding quantum is always completed at
 * the end of a body. When fewer than 24 input bits are available in an input
 * group, zero bits are added (on the right) to form an integral number of 6-bit
 * groups. Padding at the end of the data is performed using the "=" character.
 * Since all base64 input is an integral number of octets, only the following
 * cases can arise: (1) the final quantum of encoding input is an integral
 * multiple of 24 bits; here, the final unit of encoded output will be an
 * integral multiple of 4 characters with no "=" padding, (2) the final quantum
 * of encoding input is exactly 8 bits; here, the final unit of encoded output
 * will be two characters followed by two "=" padding characters, or (3) the
 * final quantum of encoding input is exactly 16 bits; here, the final unit of
 * encoded output will be three characters followed by one "=" padding
 * character.
 * </p>
 * 
 * <p>
 * Because it is used only for padding at the end of the data, the occurrence of
 * any "=" characters may be taken as evidence that the end of the data has been
 * reached (without truncation in transit). No such assurance is possible,
 * however, when the number of octets transmitted was a multiple of three and no
 * "=" characters are present.
 * </p>
 * 
 * <p>
 * Any characters outside of the base64 alphabet are to be ignored in
 * base64-encoded data.
 * </p>
 * 
 * <p>
 * Care must be taken to use the proper octets for line breaks if base64
 * encoding is applied directly to text material that has not been converted to
 * canonical form. In particular, text line breaks must be converted into CRLF
 * sequences prior to base64 encoding. The important thing to note is that this
 * may be done directly by the encoder rather than in a prior canonization step
 * in some implementations.
 * </p>
 * 
 * <p>
 * NOTE: There is no need to worry about quoting potential boundary delimiters
 * within base64-encoded bodies within multipart entities because no hyphen
 * characters are used in the base64 encoding.
 * </p>
 * </blockquote>
 * 
 * @author Stephen Ostermiller
 *         http://ostermiller.org/contact.pl?regarding=Java+Utilities
 * @since ostermillerutils 1.00.00
 */
public class Base64 {

	/**
	 * Symbol that represents the end of an input stream
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	private static final int END_OF_INPUT = -1;

	/**
	 * A character that is not a valid base 64 character.
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	private static final int NON_BASE_64 = -1;

	/**
	 * A character that is not a valid base 64 character.
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	private static final int NON_BASE_64_WHITESPACE = -2;

	/**
	 * A character that is not a valid base 64 character.
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	private static final int NON_BASE_64_PADDING = -3;

	/**
	 * This class need not be instantiated, all methods are static.
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	private Base64() {
	}

	/**
	 * Table of the sixty-four characters that are used as the Base64 alphabet:
	 * [A-Za-z0-9+/]
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	protected static final byte[] base64Chars = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/', };

	/**
	 * Reverse lookup table for the Base64 alphabet. reversebase64Chars[byte]
	 * gives n for the nth Base64 character or negative if a character is not a
	 * Base64 character.
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	protected static final byte[] reverseBase64Chars = new byte[0x100];
	static {
		// Fill in NON_BASE_64 for all characters to start with
		for (int i = 0; i < reverseBase64Chars.length; i++) {
			reverseBase64Chars[i] = NON_BASE_64;
		}
		// For characters that are base64Chars, adjust
		// the reverse lookup table.
		for (byte i = 0; i < base64Chars.length; i++) {
			reverseBase64Chars[base64Chars[i]] = i;
		}
		reverseBase64Chars[' '] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\n'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\r'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\t'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\f'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['='] = NON_BASE_64_PADDING;
	}

	/**
	 * Version number of this program
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	public static final String version = "1.2";

	/**
	 * Locale specific strings displayed to the user.
	 * 
	 * @since ostermillerutils 1.00.00
	 */
	protected static ResourceBundle labels = ResourceBundle.getBundle(
			"com.Ostermiller.util.Base64", Locale.getDefault());

	private static final int ACTION_GUESS = 0;
	private static final int ACTION_ENCODE = 1;
	private static final int ACTION_DECODE = 2;

	private static final int ARGUMENT_GUESS = 0;
	private static final int ARGUMENT_STRING = 1;
	private static final int ARGUMENT_FILE = 2;

	/**
	 * Converts the line ending on files, or standard input. Run with --help
	 * argument for more information.
	 * 
	 * @param args
	 *            Command line arguments.
	 * 
	 * @since ostermillerutils 1.00.00
	 */

}
