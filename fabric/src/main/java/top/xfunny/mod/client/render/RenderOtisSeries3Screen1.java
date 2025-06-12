package top.xfunny.mod.client.render;


import org.mtr.core.data.Lift;
import org.mtr.libraries.it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.mtr.libraries.it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;
import org.mtr.mapping.holder.*;
import org.mtr.mapping.mapper.BlockEntityRenderer;
import org.mtr.mapping.mapper.DirectionHelper;
import org.mtr.mapping.mapper.GraphicsHolder;
import org.mtr.mapping.mapper.PlayerHelper;
import org.mtr.mod.block.IBlock;
import org.mtr.mod.data.IGui;
import org.mtr.mod.render.StoredMatrixTransformations;
import top.xfunny.mod.block.SchindlerDSeriesScreen1Even;
import top.xfunny.mod.block.base.LiftPanelBase;
import top.xfunny.mod.client.resource.FontList;
import top.xfunny.mod.client.view.*;
import top.xfunny.mod.client.view.view_group.LinearLayout;
import top.xfunny.mod.item.YteGroupLiftButtonsLinker;
import top.xfunny.mod.item.YteLiftButtonsLinker;

import java.util.Comparator;

public class RenderOtisSeries3Screen1<T extends LiftPanelBase.BlockEntityBase> extends BlockEntityRenderer<T> implements DirectionHelper, IGui, IBlock {
    private final boolean isOdd;

    public RenderOtisSeries3Screen1(Argument dispatcher, Boolean isOdd) {
        super(dispatcher);
        this.isOdd = isOdd;
    }

    @Override
    public void render(T blockEntity, float tickDelta, GraphicsHolder graphicsHolder1, int light, int overlay) {
        final World world = blockEntity.getWorld2();
        if (world == null) {
            return;
        }

        final ClientPlayerEntity clientPlayerEntity = MinecraftClient.getInstance().getPlayerMapped();
        if (clientPlayerEntity == null) {
            return;
        }

        final boolean holdingLinker = PlayerHelper.isHolding(PlayerEntity.cast(clientPlayerEntity), item -> item.data instanceof YteLiftButtonsLinker || item.data instanceof YteGroupLiftButtonsLinker);
        final BlockPos blockPos = blockEntity.getPos2();
        final BlockState blockState = world.getBlockState(blockPos);
        final Direction facing = IBlock.getStatePropertySafe(blockState, FACING);

        final StoredMatrixTransformations storedMatrixTransformations = new StoredMatrixTransformations(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
        StoredMatrixTransformations storedMatrixTransformations1 = storedMatrixTransformations.copy();
        storedMatrixTransformations1.add(graphicsHolder -> {
            graphicsHolder.rotateYDegrees(-facing.asRotation());
            graphicsHolder.translate(0, 0, 7.65F / 16 - SMALL_OFFSET);
        });

        final LinearLayout parentLayout = new LinearLayout(false);
        parentLayout.setBasicsAttributes(world, blockPos);
        parentLayout.setStoredMatrixTransformations(storedMatrixTransformations1);
        parentLayout.setParentDimensions(7.5F / 16, 5F / 16);
        parentLayout.setPosition(isOdd ? -0.234375F : -0.734375F, 0.4225F);
        parentLayout.setWidth(LayoutSize.MATCH_PARENT);
        parentLayout.setHeight(LayoutSize.MATCH_PARENT);


        final LineComponent line = new LineComponent();
        line.setBasicsAttributes(world, blockPos);

        final ObjectArrayList<ObjectObjectImmutablePair<BlockPos, Lift>> sortedPositionsAndLifts = new ObjectArrayList<>();

        blockEntity.forEachTrackPosition(trackPosition -> {
            line.RenderLine(holdingLinker, trackPosition);
            SchindlerDSeriesScreen1Even.LiftCheck(trackPosition, (floorIndex, lift) -> {
                sortedPositionsAndLifts.add(new ObjectObjectImmutablePair<>(trackPosition, lift));
            });
        });

        sortedPositionsAndLifts.sort(Comparator.comparingInt(sortedPositionAndLift -> blockPos.getManhattanDistance(new Vector3i(sortedPositionAndLift.left().data))));

        if (!sortedPositionsAndLifts.isEmpty()) {
            final int count = 1;

            for (int i = 0; i < count; i++) {
                final LiftFloorDisplayView liftFloorDisplayView = new LiftFloorDisplayView();
                liftFloorDisplayView.setBasicsAttributes(world,
                        blockPos,
                        sortedPositionsAndLifts.get(i).right(),
                        FontList.instance.getFont("otis_series_3"),
                        5.5F,
                        0xFFFF0000);
                liftFloorDisplayView.setTextureId("otis_series_3_screen_1_display");
                liftFloorDisplayView.setWidth(2.6F / 16);
                liftFloorDisplayView.setHeight(2.8F / 16);
                liftFloorDisplayView.setGravity(Gravity.CENTER_VERTICAL);
                liftFloorDisplayView.setTextAlign(TextView.HorizontalTextAlign.RIGHT);
                liftFloorDisplayView.setLetterSpacing(0);
                liftFloorDisplayView.setDisplayLength(2, 0);
                liftFloorDisplayView.setMargin(1.95F / 16, 0, 0, 0);
                liftFloorDisplayView.addStoredMatrixTransformations(graphicsHolder -> graphicsHolder.translate(0, 0, -SMALL_OFFSET));

                parentLayout.addChild(liftFloorDisplayView);
            }
        }
        parentLayout.render();
    }
}