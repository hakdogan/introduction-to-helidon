/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 30.07.2021
 **/
module greeting
{
    requires transitive io.helidon.config;
    requires transitive io.helidon.webserver;
    requires transitive java.logging;
    requires java.json;

    exports org.jugistanbul.webserver.service;
}
