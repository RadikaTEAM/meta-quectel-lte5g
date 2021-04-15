
FILESEXTRAPATHS_prepend := "${THISDIR}/udhcpc/:"

SRC_URI += " \
    file://quectel-default.script \
"

do_install_append(){
    install -d      ${D}${datadir}/udhcpc/
    rm      -f      ${D}${datadir}/udhcpc/default.script
    install -m 755  ${WORKDIR}/quectel-default.script     ${D}${datadir}/udhcpc/default.script
}