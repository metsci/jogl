/*
 * License Applicability. Except to the extent portions of this file are
 * made subject to an alternative license as permitted in the SGI Free
 * Software License B, Version 1.1 (the "License"), the contents of this
 * file are subject only to the provisions of the License. You may not use
 * this file except in compliance with the License. You may obtain a copy
 * of the License at Silicon Graphics, Inc., attn: Legal Services, 1600
 * Amphitheatre Parkway, Mountain View, CA 94043-1351, or at:
 * 
 * http://oss.sgi.com/projects/FreeB
 * 
 * Note that, as provided in the License, the Software is distributed on an
 * "AS IS" basis, with ALL EXPRESS AND IMPLIED WARRANTIES AND CONDITIONS
 * DISCLAIMED, INCLUDING, WITHOUT LIMITATION, ANY IMPLIED WARRANTIES AND
 * CONDITIONS OF MERCHANTABILITY, SATISFACTORY QUALITY, FITNESS FOR A
 * PARTICULAR PURPOSE, AND NON-INFRINGEMENT.
 * 
 * NOTE:  The Original Code (as defined below) has been licensed to Sun
 * Microsystems, Inc. ("Sun") under the SGI Free Software License B
 * (Version 1.1), shown above ("SGI License").   Pursuant to Section
 * 3.2(3) of the SGI License, Sun is distributing the Covered Code to
 * you under an alternative license ("Alternative License").  This
 * Alternative License includes all of the provisions of the SGI License
 * except that Section 2.2 and 11 are omitted.  Any differences between
 * the Alternative License and the SGI License are offered solely by Sun
 * and not by SGI.
 *
 * Original Code. The Original Code is: OpenGL Sample Implementation,
 * Version 1.2.1, released January 26, 2000, developed by Silicon Graphics,
 * Inc. The Original Code is Copyright (c) 1991-2000 Silicon Graphics, Inc.
 * Copyright in any portions created by third parties is as indicated
 * elsewhere herein. All Rights Reserved.
 * 
 * Additional Notice Provisions: The application programming interfaces
 * established by SGI in conjunction with the Original Code are The
 * OpenGL(R) Graphics System: A Specification (Version 1.2.1), released
 * April 1, 1999; The OpenGL(R) Graphics System Utility Library (Version
 * 1.3), released November 4, 1998; and OpenGL(R) Graphics with the X
 * Window System(R) (Version 1.3), released October 19, 1998. This software
 * was created using the OpenGL(R) version 1.2.1 Sample Implementation
 * published by SGI, but has not been independently verified as being
 * compliant with the OpenGL(R) version 1.2.1 Specification.
 */

package com.sun.opengl.impl.mipmap;

import java.nio.*;

/**
 *
 * @author  Administrator
 */
public class Type_Widget {
  
  ByteBuffer buffer;
  
  /** Creates a new instance of Type_Widget */
  public Type_Widget() {
    buffer = ByteBuffer.allocate( 4 );
  }
  
  public void setUB0( byte b ) {
    buffer.position( 0 );
    buffer.put( b );
  }
  
  public byte getUB0() {
    buffer.position( 0 );
    return( buffer.get() );
  }
  
  public void setUB1( byte b ) {
    buffer.position( 1 );
    buffer.put( b );
  }
  
  public byte getUB1() {
    buffer.position( 1 );
    return( buffer.get() );
  }
  
  public void setUB2( byte b ) {
    buffer.position( 2 );
    buffer.put( b );
  }
  
  public byte getUB2() {
    buffer.position( 2 );
    return( buffer.get() );
  }
  
  public void setUB3( byte b ) {
    buffer.position( 3 );
    buffer.put( b );
  }
  
  public byte getUB3() {
    buffer.position( 3 );
    return( buffer.get() );
  }
  
  public void setUS0( short s ) {
    buffer.position( 0 );
    buffer.putShort( s );
  }
  
  public short getUS0() {
    buffer.position( 0 );
    return( buffer.getShort() );
  }
  
  public void setUS1( short s ) {
    buffer.position( 2 );
    buffer.putShort( s );
  }
  
  public short getUS1() {
   buffer.position( 2 );
   return( buffer.getShort() );
  }
  
  public void setUI( int i ) {
    buffer.position( 0 );
    buffer.putInt( i );
  }
  
  public int getUI() {
    buffer.position( 0 );
    return( buffer.getInt() );
  }
  
  public void setB0( byte b ) {
    buffer.position( 0 );
    buffer.put( b );
  }
  
  public byte getB0() {
    buffer.position( 0 );
    return( buffer.get() );
  }
  
  public void setB1( byte b ) {
    buffer.position( 1 );
    buffer.put( b );
  }
  
  public byte getB1() {
    buffer.position( 1 );
    return( buffer.get() );
  }
  
  public void setB2( byte b ) {
    buffer.position( 2 );
    buffer.put( b );
  }
  
  public byte getB2() {
    buffer.position( 2 );
    return( buffer.get() );
  }
  
  public void setB3( byte b ) {
    buffer.position( 3 );
    buffer.put( b );
  }
  
  public byte getB3() {
    buffer.position( 3 );
    return( buffer.get() );
  }
  
  public void setS0( short s ) {
    buffer.position( 0 );
    buffer.putShort( s );
  }
  
  public short getS0() {
    buffer.position( 0 );
    return( buffer.getShort() );
  }
  
  public void setS1( short s ) {
    buffer.position( 2 );
    buffer.putShort( s );
  }
  
  public short getS1() {
    buffer.position( 2 );
    return( buffer.getShort() );
  }
  
  public void setI( int i ) {
    buffer.position( 0 );
    buffer.putInt( i );
  }
  
  public int getI() {
    buffer.position( 0 );
    return( buffer.getInt() );
  }
  
  public void setF( float f ) {
    buffer.position( 0 );
    buffer.putFloat( f );
  }
  
  public float getF() {
    buffer.position( 0 );
    return( buffer.getFloat() );
  }
  
  public ByteBuffer getBuffer() {
    buffer.rewind();
    return( buffer );
  }
  
  public static void main( String args[] ) {
    Type_Widget t = new Type_Widget();
    t.setI( 1000000 );
    
    System.out.println("int: " + Integer.toHexString( t.getI() ) );
    
  }
}
