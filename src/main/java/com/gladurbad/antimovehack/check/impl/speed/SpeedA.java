package com.gladurbad.antimovehack.check.impl.speed;

import com.gladurbad.antimovehack.check.Check;
import com.gladurbad.antimovehack.check.CheckInfo;
import com.gladurbad.antimovehack.playerdata.PlayerData;
import com.gladurbad.antimovehack.util.CollisionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

@CheckInfo(name = "Speed", type = "A", dev = false)
public class SpeedA extends Check {

    public SpeedA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(PlayerMoveEvent event) {
        final Player player = data.getPlayer();

        final double deltaXZ = Math.hypot(data.deltaX, data.deltaZ);
        final double lastDeltaXZ = Math.hypot(data.lastDeltaX, data.lastDeltaZ);

        final double EPSILON = 0.027;
        final double prediction = lastDeltaXZ * 0.91F;
        final double difference = Math.abs(prediction - deltaXZ);

        if (difference > EPSILON && !CollisionUtil.isOnGround(player)) {
            increaseBuffer();
            if (buffer > 5) {
                failAndSetback();
            }
        } else {
            decreaseBuffer();
            setLastLegitLocation(player.getLocation());
        }
    }
}