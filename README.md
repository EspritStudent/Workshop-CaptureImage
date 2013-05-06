Workshop-CaptureImage
=====================

Workshop-CaptureImage 
put dsj.dll into your jre / jdk's bin directory or the System folder (or use command line args to set the libary path).
The dll will be loaded by code in dsj.jar when needed. You do not need and are not supposed to call System.loadLibrary 
or System.load(..) yourself. If you need to load the dll from some place not on the java library path,
use DSEnvironment.setDLLPath as your first call to dsj.

As off dsj 0_8_6 the dll is also available as a 64bit build.

