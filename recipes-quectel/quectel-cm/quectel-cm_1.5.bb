SUMMARY = "Quectel Connection Manager"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
DESCRIPTION = "Quectel Connection Manager for LTE&5G modems"
FILESEXTRAPATHS_prepend := "${THISDIR}/quectel-CM/v${PV}:"


DEPENDS = "resolvconf"

SRC_URI = " \
    file://Makefile \
    file://default.script \
    file://device.c \
    file://GobiNetCM.c \
    file://main.c \
    file://mbim-cm.c \
    file://MPQCTL.h \
    file://MPQMI.h \
    file://MPQMUX.c \
    file://MPQMUX.h \
    file://qmap_bridge_mode.c \
    file://QMIThread.c \
    file://QMIThread.h \
    file://QmiWwanCM.c \
    file://quectel-qmi-proxy.c \
    file://udhcpc.c \
    file://util.c \
    file://util.h \
"

S = "${WORKDIR}"
B = "${S}"

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CFLAGS=${CFLAGS}' \
    'LFLAGS=${LDFLAGS}' \
"

do_install () {
    install -d      ${D}${sbindir}
    install -m 755  ${WORKDIR}/quectel-CM         ${D}${sbindir}
    install -m 755  ${WORKDIR}/quectel-qmi-proxy  ${D}${sbindir}
}

# Mark the files which are part of this package
FILES_${PN} += "${sbindir}/quectel-config"
FILES_${PN} += "${sbindir}/quectel-CM"
FILES_${PN} += "${sbindir}/quectel-qmi-proxy"
FILES_${PN} += "${datadir}/udhcpc/default.script"
