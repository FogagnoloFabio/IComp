
package it.fogagnolo.icomp;

public enum GroupState {
    NONE_SELECTED(0), SOME_SELECTED(5), ALL_SELECTED(9);

    private int value;

    private GroupState(int value) {

        this.value = value;
    }
};
