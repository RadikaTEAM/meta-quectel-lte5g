SUMMARY = "Quectel Modem Support"
SECTION = "net"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
DESCRIPTION = "Package Group for Quectel LTE&5G Modems"

inherit packagegroup

RDEPENDS_${PN} = " \
    quectel-ppp \
    quectel-cm \
"

PREFERRED_VERSION_linux-raspberrypi ??= "5.4%"
PREFERRED_VERSION_quectel-cm ??= "1.5"
PREFERRED_VERSION_quectel-ppp ??= "1.0"