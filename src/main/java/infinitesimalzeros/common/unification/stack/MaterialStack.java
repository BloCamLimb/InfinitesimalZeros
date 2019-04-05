package infinitesimalzeros.common.unification.stack;

import net.minecraft.block.material.Material;

public class MaterialStack {
	
    public final Material material;
    public final long amount;

    public MaterialStack(Material material, long amount) {
        this.material = material;
        this.amount = amount;
    }

    public MaterialStack copy(long amount) {
        return new MaterialStack(material, amount);
    }

    public MaterialStack copy() {
        return new MaterialStack(material, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaterialStack that = (MaterialStack) o;

        if (amount != that.amount) return false;
        return material.equals(that.material);
    }

    @Override
    public int hashCode() {
        return material.hashCode();
    }

}
