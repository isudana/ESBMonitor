CREATE TABLE HTTP_LOG(
  activeThreadCount INTEGER,
  avgSizeRecieved DECIMAL(3,2),
  avgSizeSent DECIMAL(3,2),
  faultsRecieving INTEGER,
  faultsSending INTEGER,
  messagesRecieved INTEGER,
  messageSent INTEGER,
  queueSize INTEGER,
  time TIMESTAMP,
  requestType INTEGER
);