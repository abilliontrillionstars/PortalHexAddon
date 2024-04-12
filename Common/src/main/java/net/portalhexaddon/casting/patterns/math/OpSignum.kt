package net.portalhexaddon.casting.patterns.math

import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import com.mojang.math.Quaternion
import com.mojang.math.Vector3f
import net.minecraft.world.phys.Vec3
import qouteall.imm_ptl.core.api.PortalAPI
import qouteall.imm_ptl.core.portal.Portal
import qouteall.imm_ptl.core.portal.PortalManipulation
import qouteall.q_misc_util.my_util.DQuaternion
import qouteall.q_misc_util.my_util.RotationHelper
import kotlin.math.absoluteValue
import kotlin.math.log
import kotlin.math.sign

class OpSignum : SpellAction {
    override val argc: Int = 4


    /**
     * The method called when this Action is actually executed. Accepts the [args]
     * that were on the stack (there will be [argc] of them), and the [ctx],
     * which contains things like references to the caster, the ServerLevel,
     * methods to determine whether locations and entities are in ambit, etc.
     * Returns the list of iotas that should be added back to the stack as the
     * result of this action executing.
     */
    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
        val PrtPos: Vec3 = args.getVec3(0,argc)
        val PrtPosOut: Vec3 = args.getVec3(1,argc)
        val PrtRot: Vec3 = args.getVec3(2,argc)
        val PrtSize: Double = args.getDouble(3,argc)

        //ctx.assertVecInRange(PrtPos)
        val PrtPosT = Vector3f(PrtPos.x.toFloat(), PrtPos.y.toFloat(), PrtPos.z.toFloat())
        val PrtPosOutT = Vector3f(PrtPosOut.x.toFloat(), PrtPosOut.y.toFloat(), PrtPosOut.z.toFloat())
        val PrtRotT = Vector3f((PrtRot.x).toFloat(), (PrtRot.y).toFloat(), (PrtRot.z).toFloat())

        return Triple(
            Spell(PrtPosT,PrtPosOut,PrtRot,PrtSize),
            0,
            listOf(ParticleSpray.burst(ctx.caster.position(), 1.0))
        )

    }

    private data class Spell(val PrtPos: Vector3f,val PrtPosOut: Vec3,val PrtRot: Vec3, val PrtSize: Double) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            val level = ctx.world
            val portal: Portal? = Portal.entityType.create(level)
            val PrtRotPrp = Vec3((-1.0*PrtRot.y).toDouble(),PrtRot.x,0.0)

            portal!!.originPos = Vec3(PrtPos)
            portal.setDestinationDimension(ctx.world.dimension())
            portal.setDestination(PrtPosOut)
            portal.setOrientationAndSize(
                //Vec3(1.0,0.0,0.0).multiply(PrtRot),
                //Vec3(0.0,1.0,0.0).multiply(PrtRotPrp),
                perpendicular3DVecs(-PrtRot.y,PrtRot.x,PrtRot.z),
                Vec3(PrtRot.y,PrtRot.x,PrtRot.z),
                PrtSize,
                PrtSize
            )
            print(PrtRot)
            print(perpendicular3DVecs(PrtRot.x,PrtRot.y,PrtRot.z))
            //PortalAPI.setPortalOrientationQuaternion(portal, DQuaternion(1.0.times(PrtRot.x),1.0.times(PrtRot.y),1.0.times(PrtRot.z),180.0))
            //DQuaternion(1.0,0.0,0.0,90.0*PrtRot.x)
            //PortalManipulation.setPortalOrientationQuaternion(portal,DQuaternion(1.0,0.0,0.0,90.0*PrtRot.x))
            portal.reloadAndSyncToClient()

            portal.level.addFreshEntity(portal)
            println(PrtRot)
        }
        private fun perpendicular3DVecs(x: Double,y: Double, z: Double): Vec3 {
            val s: Double = (x*x + y*y + z*z);
            var g: Double = 1.0
            //val g: Double = (s*(z*(1/(z+0.001))));  // note s instead of 1
            when (z){
            0.0 -> {
                g = 1.0
            }
            else -> {
               g = (s*(z*(1/(z))))
            }}
            val h: Double = z + g;
            return Vec3(g*h - x*x, -x*y, -x*h)
        }
        //private fun perpendicular3DVecs(a: Double,b: Double, c: Double): Vec3 {
        //    if (c > a){
        //        return Vec3(b,-a,0.0).normalize()
        //    } else {
        //        return Vec3(0.0,-c,b).normalize()
        //    }
        //}
    }
}