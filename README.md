# meta-quectel-lte5g

This repository contains the Yocto Project layer for working with Quectel's USB Modems.

This layer adds kernel modifications to support the following Quectel hardware: 

* EC2X LTE Module
* EG9X LTE Module
* EG2X-G LTE Module
* EM05 LTE Module
* AGXX Automotive Module
* EX06 LTE-A Module
* EX12 LTE-A Module
* EG18 LTE-A Module
* EM20 LTE-A Module
* BGXX LPWA Module
* RX500 5G Module
* RX510 5G Module

Once the Linux Kernel adds support for a module it can be removed from 
this layer.

## Dependencies

This layer depends on:

* __Bitbake__
 URI: git://git.openembedded.org/bitbake
 branch: master

* __Open Embedded Core__
 URI: git://git.openembedded.org/openembedded-core
 layers: meta
 branch: master

## Recipes
This layer consists of two sets of recipes.

* Kernel modifications required by the USB modems
* Quectel's test applications

### Kernel modifications
Quectel's USB modems require some specific changes to the serial, QMI and GobiNet drivers of the Linux Kernel. These changed were done following Quectel's "Quectel LTE & 5G Linux USB Driver Guide V2.0" under "Quectel_LTE5G_LINUX_USB_DRIVER_V1.0.zip". 

> __Note:__ Currently, the kernel recipe is only available for the 5.4 version. 

> __Note:__ Quectel provides kernel files for previous version but patching a kernel version above 5.4 requires to modify the kernel using the Quectel LTE & 5G Linux USB Driver Guide v2.0 file and generate the patch file. Please submit your kernel patch file as a pull request to this repository.

### Quectel Connection Manager
Quectel provides its QMI/GobiNet Connection Manager for their USB Modems. This is Quectel's recommended workflow to use their modems as PPP creates some overhead, causing decreased throughputs.

### Quectel PPP
Includes the recomended scripts to setup a PPP server using Quectel's modems. This solution is architecture independent, as only requires to include PPP into the distribution.


## Adding the quectel-community layer to your build

Append the layer's path to `bblayers.conf` or use `bitbake-layers add path/to/meta-quectel-lte5g`

```
BBLAYERS ?= " \
  /path/to/yocto/meta \
  /path/to/yocto/meta-poky \
  /path/to/yocto/meta-yocto-bsp \
  /path/to/yocto/meta-quectel-lte5g \
  "
```

### Choosing the driver type

It required to include the driver type either `local.conf`, `distro.conf` of the `image.bb` files. Both QMI and GobiNet drivers are not meant to be installed togheter, as mentioned in the Quectel LTE & 5G Linux USB Driver Guide v2.0.

```
MODEM_DRIVER = "qmi"      # QMI driver enabled
MODEM_DRIVER = "gobinet"  # GobiNet driver enabled
```

## Using Quectel's Connection Manager
Quectel's CM uses the QMI/GobiNet Linux Drivers to establish a link between the host and the USB modem. 

The CM depends on `resolvconf` to include the USB modem interface (usbX, wwanX, etc...) in the network routing tables. See the udcpc recipe `.bbapend` file.

Run the following instruction to enable the new network interface.

``` bash 
 quectel-CM -s $APN     \ # Access Point Name 
               $USER    \ # Username
               $PWD     \ # Password
               $AUTH    \ # Auth Method (None=0, PAP=1, CHAP=2)
            -p $PINCODE \ # SIM Pincode
            -f $LOGFILE \ # Save quectel-CM output to a logfile
            -i $IFACE   \ # Select interface name if many modems are connected
            -v          \ # Enable verbose
            -4            # Use IPV4 (Default)
          # -6            # Use IPV6
```

## Using the PPPd scripts

Once your Quectel hardware is connected and the appropreate USB driver is associated with it you can initiate a call with: 

``` bash
pppd call quectel-ppp
```

Before using the script you will need to set the appropriate Access Point Name (APN), Username and Password for your LTE network connection.  To do this set `$LTE_APN`, `$LTE_USERNAME` and `$LTE_PASSWORD` environment variables before calling the pppd script.

It is recommended to add a bbappend file to replace the evironmental variables during the build process or at the first boot.
