package com.sofakingforever.stars.entities

import com.sofakingforever.stars.entities.stars.BaseStar
import com.sofakingforever.stars.entities.stars.TinyStar

object InterstellarFactory {

    fun create(starConstraints: StarConstraints, x: Int, y: Int, color: Int, listener: BaseStar.StarCompleteListener): Star {



            return TinyStar(starConstraints, x, y, color, listener)

    }

}