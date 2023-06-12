//package com.balt.get_hadning_utils_xml;
//
//import java.util.List;
//
//import javax.usb.UsbConfiguration;
//import javax.usb.UsbDevice;
//import javax.usb.UsbDeviceDescriptor;
//import javax.usb.UsbEndpoint;
//import javax.usb.UsbHostManager;
//import javax.usb.UsbHub;
//import javax.usb.UsbInterface;
//import javax.usb.UsbInterfacePolicy;
//import javax.usb.UsbPipe;
//
//public class UsbTest {
//    private static short idVendor = (short)0x04E8;
//    private static short idProduct = (short)0x6860;
//
//    public static void main(String[] args) {
//        try {
//            UsbPipe sendUsbPipe = new UsbTest().useUsb();
//
//            if (sendUsbPipe != null) {
//                byte[] buff = new byte[64];
//                for (int i = 0; i < 9; i++) {
//                    buff[i] = (byte)i;
//                    sendMassge(sendUsbPipe, buff);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public UsbPipe useUsb() throws Exception{
//        UsbInterface iface = linkDevice();
//        if (iface == null) {
//            return null;
//        }
//        UsbEndpoint receivedUsbEndpoint,sendUsbEndpoint;
//
//        sendUsbEndpoint = (UsbEndpoint)iface.getUsbEndpoints().get(0);
//        if (!sendUsbEndpoint.getUsbEndpointDescriptor().toString().contains("OUT")) {
//            receivedUsbEndpoint = sendUsbEndpoint;
//            sendUsbEndpoint = (UsbEndpoint)iface.getUsbEndpoints().get(1);
//        } else {
//            receivedUsbEndpoint = (UsbEndpoint)iface.getUsbEndpoints().get(1);
//        }
//
//// send:
//        UsbPipe sendUsbPipe =  sendUsbEndpoint.getUsbPipe();
//        sendUsbPipe.open();
//
//// reception
//        final UsbPipe receivedUsbPipe =  receivedUsbEndpoint.getUsbPipe();
//        receivedUsbPipe.open();
//
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    receivedMassge(receivedUsbPipe);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        return sendUsbPipe;
//    }
//
//    public UsbInterface linkDevice() throws Exception{
//
//        UsbDevice device = null;
//        if (device == null) {
//            device = findDevice(UsbHostManager.getUsbServices()
//                    .getRootUsbHub());
//        }
//        if (device == null) {
//            System.out.println ( "No device found!");
//            return null;
//        }
//        UsbConfiguration configuration = device.getActiveUsbConfiguration();
//        UsbInterface iface = null;
//        if (configuration.getUsbInterfaces().size() > 0) {
//            iface = configuration.getUsbInterface((byte) 1);
//        } else {
//            return null;
//        }
//        iface.claim(new UsbInterfacePolicy()
//        {
//            @Override
//            public boolean forceClaim(UsbInterface usbInterface)
//            {
//                return true;
//            }
//        });
//        return iface;
//    }
//
//    public void receivedMassge(UsbPipe usbPipe) throws Exception{
//        byte[] b = new byte[64];
//        int length = 0;
//        while (true) {
//            length = usbPipe.syncSubmit (b); // blocked
//            System.out.println ( "receiving length:" + length);
//            for (int i = 0; i < length; i++) {
//                System.out.print(Byte.toUnsignedInt(b[i])+" ");
//            }
//        }
//    }
//
//    public static void sendMassge(UsbPipe usbPipe,byte[] buff)  throws Exception{
//        usbPipe.syncSubmit (buff); // blocked
////usbPipe.asyncSubmit(buff);// non-blocking
//    }
//
//    public UsbDevice findDevice(UsbHub hub)
//    {
//        UsbDevice device = null;
//        List list = (List) hub.getAttachedUsbDevices();
//        for (int i = 0;i<list.size();i++)
//        {
//            device = (UsbDevice)list.get(i);
//            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
//            System.out.println(i+"___"+Integer.toHexString(desc.idVendor())+"___"+Integer.toHexString(desc.idProduct()));
//            if (desc.idVendor() == idVendor && desc.idProduct() == idProduct) {return device;}
//            if (device.isUsbHub())
//            {
//                device = findDevice((UsbHub) device);
//                if (device != null) return device;
//            }
//        }
//        return null;
//    }
//}