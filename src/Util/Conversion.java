package Util;


public class Conversion {
	/**
     * Convert a string into a byte array.
     */
    public static byte[] convertToByteArray( String s )
    {
        try {
            // see the following page for character encoding
            // http://java.sun.com/products/jdk/1.1/docs/guide/intl/encoding.doc.html
            return s.getBytes( "UTF8" );
        } catch ( java.io.UnsupportedEncodingException uee ) {
            uee.printStackTrace();
            throw new Error( "Platform doesn't support UTF8 encoding" );
        }
    }


    /**
     * Convert a byte into a byte array.
     */
    public static byte[] convertToByteArray( byte n )
    {
        n = (byte)( n ^ ( (byte) 0x80 ) );
        return new byte[] { n };
    }


    /**
     * Convert a short into a byte array.
     * it works only on Windows PC
     */
    public static byte[] convertToByteArray( short n )
    {
        byte[] key = new byte[ 2 ];
        pack2( key, 0, n );
        return key;
    }
    static final void pack2( byte[] data, int offs, int val )
    {
    	data[offs++] = (byte) val;
    	data[offs++] = (byte) ( val >> 8 );
    }
    static short unpack2( byte[] buf, int offset )
    {
    	short value = (short)(( (buf[offset+1]<<8) & ((short)0xFF00) ) | ( (buf[offset]<<0) & ((short)0x00FF) ));
    	return value;
    }
    public static short convertToShort( byte[] buf ) {
    	short value = unpack2( buf, 0 );
    	return value;
    }
    public static short convertToShort( byte[] buf, int offset ) {
    	short value = unpack2( buf, offset );
    	return value; 
    }

    /**
     * Convert an integer into a byte array.
     */
    public static byte[] convertToByteArray( int n )
    {
        n = (n ^ 0x80000000);
        byte[] key = new byte[4];
        pack4(key, 0, n);
        return key;
    }


    /**
     * Convert a long into a byte array.
     */
    public static byte[] convertToByteArray( long n )
    {
        n = (n ^ 0x8000000000000000L); // flip MSB because "long" is signed
        byte[] key = new byte[8];
        pack8( key, 0, n );
        return key;
    }


    /**
     * Convert a byte array (encoded as UTF-8) into a String
     */
    public static String convertToString( byte[] buf )
    {
        try {
            // see the following page for character encoding
            // http://java.sun.com/products/jdk/1.1/docs/guide/intl/encoding.doc.html
            return new String( buf, "UTF8" );
        } catch ( java.io.UnsupportedEncodingException uee ) {
            uee.printStackTrace();
            throw new Error( "Platform doesn't support UTF8 encoding" );
        }
    }


    /**
     * Convert a byte array into an integer (signed 32-bit) value.
     */
    public static int convertToInt( byte[] buf )
    {
        int value = unpack4( buf, 0 );
        value = ( value ^ 0x80000000 ); // flip MSB because "int" is signed
        return value;
    }
    public static int convertToInt_little_endian( byte[] buf )
    {
    	int value = unpack4_little_endian(buf, 0);
    	return value;
    }

    /**
     * Convert a byte array into a long (signed 64-bit) value.
     */
    public static long convertToLong( byte[] buf )
    {
        long value = ( (long) unpack4( buf, 0 ) << 32  )
                     + ( unpack4( buf, 4 ) & 0xFFFFFFFFL );
        value = ( value ^ 0x8000000000000000L ); // flip MSB because "long" is signed
        return value;
    }

    static int unpack4( byte[] buf, int offset )
    {
        int value = ( buf[ offset ] << 24 )
            | ( ( buf[ offset+1 ] << 16 ) & 0x00FF0000 )
            | ( ( buf[ offset+2 ] << 8 ) & 0x0000FF00 )
            | ( ( buf[ offset+3 ] << 0 ) & 0x000000FF );

        return value;
    }
    
    static int unpack4_little_endian( byte[] buf, int offset )
    {
    	int value = ( buf[offset+3] << 24 )
    		| ( ( buf[offset+2] << 16 ) & 0x00FF0000 )
    		| ( ( buf[offset+1] << 8 ) & 0x0000FF00 )
    		| ( ( buf[offset] << 0 ) & 0x000000FF );
    	return value;
    }

    static final void pack4( byte[] data, int offs, int val )
    {
        data[offs++] = (byte) ( val >> 24 );
        data[offs++] = (byte) ( val >> 16 );
        data[offs++] = (byte) ( val >> 8 );
        data[offs++] = (byte) val;
    }


    static final void pack8( byte[] data, int offs, long val )
    {
        pack4( data, 0, (int) ( val >> 32 ) );
        pack4( data, 4, (int) val );
    }


    /**
     * Test static methods
     */
    public static void main( String[] args )
    {
        byte[] buf;

        buf = convertToByteArray( (short)32767 );
        System.out.println( "short value of 65530 is: " + convertToShort( buf ) );
        
        buf = convertToByteArray( (int) 5 );
        System.out.println( "int value of 5 is: " + convertToInt( buf ) );

        buf = convertToByteArray( (int) -1 );
        System.out.println( "int value of -1 is: " + convertToInt( buf ) );

        buf = convertToByteArray( (int) 22111000 );
        System.out.println( "int value of 22111000 is: " + convertToInt( buf ) );

        buf = convertToByteArray( (long) 5L );
        System.out.println( "long value of 5 is: " + convertToLong( buf ) );

        buf = convertToByteArray( (long) -1L );
        System.out.println( "long value of -1 is: " + convertToLong( buf ) );

        buf = convertToByteArray( (long) 1112223334445556667L );
        System.out.println( "long value of 1112223334445556667 is: " + convertToLong( buf ) );
    }
}

