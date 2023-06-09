package com.stgcodes.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class DatabaseListener  {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAfterCommit(DatabaseEvent event) {
        logMsg("After commit");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
          public void onAfterRollback(DatabaseEvent event) {
        logMsg("ROLL BACK!!!");
    }

    private void logMsg(String msg) {
        log.debug("*********************************************");
      log.debug(msg);
        log.debug("*********************************************");
}

}
