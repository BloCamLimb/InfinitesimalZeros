package infinitesimalzeros.client.render.machine;

import infinitesimalzeros.common.util.IZUtils;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Smelter - BloCamLimb
 * Created using Tabula 7.0.1
 */
public class ModelSmelter extends ModelBase {
    public ModelRenderer shape1;

    public ModelSmelter() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1, 0.0F);
    }

    public void render(float size) { 
    	
        this.shape1.render(size);
        
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
