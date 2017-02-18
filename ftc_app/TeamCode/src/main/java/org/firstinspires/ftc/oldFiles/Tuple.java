package org.firstinspires.ftc.oldFiles;

/**
 * Created by Plotnw on 10/25/2016.
 */

public class Tuple <L, R> {
        //Fairly simple object, to store the movement values. This will allow the grid functions to remain pure, and only have one impure function
        private final L left;
        private final R right;

        public Tuple(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() { return left; }
        public R getRight() { return right; }

}
