package top.xfunny.mod.block;

import org.mtr.mapping.holder.BlockPos;
import org.mtr.mapping.holder.BlockRenderType;
import org.mtr.mapping.holder.BlockState;
import org.mtr.mapping.holder.Item;
import org.mtr.mapping.mapper.BlockEntityExtension;
import org.mtr.mod.block.BlockPSDAPGDoorBase;
import top.xfunny.mod.BlockEntityTypes;
import top.xfunny.mod.Items;
import top.xfunny.mod.block.base.OldBlockPSDAPGDoorBase;

import javax.annotation.Nonnull;

public class OtisE411USDoor1 extends OldBlockPSDAPGDoorBase {

    @Nonnull
    @Override
    public BlockEntityExtension createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BlockEntity(blockPos, blockState);
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType2(BlockState state) {
        return BlockRenderType.getModelMapped();
    }

    @Nonnull
    @Override
    public Item asItem2() {
        return Items.OTIS_E411_US_DOOR_1.get();
    }

    public static class BlockEntity extends BlockEntityBase {

        public BlockEntity(BlockPos pos, BlockState state) {
            super(BlockEntityTypes.OTIS_E411_US_DOOR_1.get(), pos, state);
        }
    }
}