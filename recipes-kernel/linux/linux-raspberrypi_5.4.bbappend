QUECTEL_LTE5G_VERSION = "1.0"
KERNEL_VERSION = "5.4"

FILESEXTRAPATHS_prepend := "${THISDIR}/patches/kernel-v${KERNEL_VERSION}/:"
FILESEXTRAPATHS_prepend := "${THISDIR}/drivers/quectel-lte5g-v${QUECTEL_LTE5G_VERSION}/gobinet:"
FILESEXTRAPATHS_prepend := "${THISDIR}/drivers/quectel-lte5g-v${QUECTEL_LTE5G_VERSION}/qmi:"

SRC_URI += " \
    file://001-raspberrypi-usb-serial-option-driver.patch \
    file://GobiUSBNet.c \
    file://QMI.c        \
    file://QMI.h        \
    file://QMIDevice.c  \
    file://QMIDevice.h  \
    file://Structs.h    \
    file://qmi_wwan_q.c \
"

# Conditionally patch the kernel depending on the content of the ${MODEM_DRIVER} variable
SRC_URI += " \
    ${@bb.utils.contains('MODEM_DRIVER', 'gobinet', 'file://002-raspberrypi-gobinet-driver.patch', '', d)} \
    ${@bb.utils.contains('MODEM_DRIVER', 'qmi', 'file://003-raspberrypi-qmi-wwan-driver.patch', '', d)} \
"

do_configure_prepend() {
    cp -n ${WORKDIR}/GobiUSBNet.c  ${S}/drivers/net/usb/
    cp -n ${WORKDIR}/QMI.c         ${S}/drivers/net/usb/
    cp -n ${WORKDIR}/QMI.h         ${S}/drivers/net/usb/
    cp -n ${WORKDIR}/QMIDevice.c   ${S}/drivers/net/usb/
    cp -n ${WORKDIR}/QMIDevice.h   ${S}/drivers/net/usb/
    cp -n ${WORKDIR}/Structs.h     ${S}/drivers/net/usb/
    cp -n ${WORKDIR}/qmi_wwan_q.c  ${S}/drivers/net/usb/    

    kernel_configure_variable USB_NET_QMI_WWAN  y
    kernel_configure_variable USB_USBNET        y
    kernel_configure_variable USB_SERIAL_OPTION y
    kernel_configure_variable USB_SERIAL        y
    kernel_configure_variable PPP               y
    kernel_configure_variable PPP_ASYNC         y
    kernel_configure_variable PPP_SYNC_TTY      y
    kernel_configure_variable PPP_DEFLATE       y
}
