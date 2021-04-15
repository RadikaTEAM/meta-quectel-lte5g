SUMMARY = "PPP Scripts for Basic Quectel Chip Operation"
SECTION = "net"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
DESCRIPTION = "A set of Linux PPP scripts that provide connect, disconnect and other basic Point-to-Point Protocol functionality for Quectel wireless modems.  "

RDEPENDS_${PN} = "ppp"

SRC_URI = " \
    file://quectel-ppp \
    file://quectel-chat-connect \
    file://quectel-chat-disconnect \
"

S = "${WORKDIR}"
B = "${S}/quectel-CM"

#inherit allarch update-rc.d

INITSCRIPT_NAME = "usb-modem"
INITSCRIPT_PARAMS = "defaults 15"

do_install () {
    install -d      ${D}${sysconfdir}/ppp/peers
    install -m 0755 ${S}/quectel-ppp                ${D}${sysconfdir}/ppp/peers/
    install -m 0755 ${S}/quectel-chat-connect       ${D}${sysconfdir}/ppp/peers/
    install -m 0755 ${S}/quectel-chat-disconnect    ${D}${sysconfdir}/ppp/peers/
}

FILES_${PN} += "{sysconfdir}/ppp/peers/quectel-ppp"
FILES_${PN} += "{sysconfdir}/ppp/peers/quectel-chat-connect"
FILES_${PN} += "{sysconfdir}/ppp/peers/quectel-chat-disconnect"
FILES_${PN} += "{sysconfdir}/ppp/peers/generic-modem"

