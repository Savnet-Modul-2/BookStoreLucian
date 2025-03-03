package com.javaacademy.learning.bookstore.entities;


public enum ReservationStatus {
    PENDING {
        public boolean isNextState(ReservationStatus nextState) {
            boolean result = false;
            if (nextState == CANCELED || nextState == PENDING)
                result = true;
            return result;
        }


    },
    IN_PROGRESS {
        public boolean isNextState(ReservationStatus nextState) {
            boolean result = false;
            if (nextState == FINISHED)
                result = true;
            return result;
        }

    },
    DELAYED {
        public boolean isNextState(ReservationStatus nextState) {
            boolean result = false;
            if (nextState == PENDING)
                result = true;
            return result;
        }

    },
    FINISHED {
        public boolean isNextState(ReservationStatus nextState) {
            boolean result = false;
            if (nextState == PENDING || nextState == DELAYED || nextState == IN_PROGRESS)
                result = true;
            return result;
        }

    },
    CANCELED {
        public boolean isNextState(ReservationStatus nextState) {
            boolean result = false;
            if (nextState == PENDING || nextState == DELAYED || nextState == IN_PROGRESS)
                result = true;
            return result;
        }

    };
    public abstract boolean isNextState(ReservationStatus nextState);
}
