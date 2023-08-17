plugins {
    id("net.civmc.civgradle")
    id("io.papermc.paperweight.userdev")
    id("xyz.jpenilla.run-paper")
}

dependencies {
    paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    compileOnly("net.civmc.civmodcore:paper:2.0.0-SNAPSHOT:dev-all")
    compileOnly("net.civmc.namelayer:namelayer-paper:3.0.3:dev")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.8.0")
    compileOnly("net.civmc.citadel:citadel-paper:5.1.2:dev")
    compileOnly("net.civmc.bastion:bastion-paper:3.0.1:dev")
    compileOnly("net.civmc.finale:finale-paper:2.1.0")
}